package com.gilboot.easypark.util

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap

//val database: FirebaseFirestore = Firebase.firestore
//val storage1: FirebaseStorage = Firebase.storage
//val userCollections: CollectionReference = database.collection("users")
private lateinit var database: FirebaseDatabase
private lateinit var reference: DatabaseReference

/**
 * Note: For the password, most of these functions work with a CharArray instead of String objects.
 * That’s because objects like String are immutable.
 * A CharArray can be overwritten, which allows you to erase the sensitive information from memory after you’re done with it
 * The encryption_method puts the salt used in encryption, the encrypted password, and the iv -> the 16 Byte random data used during encryption
 * into a HashMap that can be stored in the firebase database in the field of passwords
 * The three; salt, encrypted password and the iv will be needed in decryption
 * We have to store the HashMap in the field for the password because we have to keep track of the salt for every password used
 */
fun encryption_method(str_password: String): HashMap<String,Any>{
    // First encryption step is getting the salt
    val random = SecureRandom()
    val salt = ByteArray(256)
    random.nextBytes(salt)

    // Now, generating the secret key with the users password and the salt.
    // Add the following right under the code you just added:
    // Password and the salt and generate the key: (password, salt) -> key
    // str -> password
    val pwd = str_password.toCharArray()
    val pbKeySpec = PBEKeySpec(pwd, salt, 1324, 256) // 1
    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1") // 2
    val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded // 3
    val keySpec = SecretKeySpec(keyBytes, "AES") // 4

    // here you, created 16 bytes of random data.
    // Packaged it into an IvParameterSpec object.
    val ivRandom = SecureRandom() //1
    val iv = ByteArray(16)
    ivRandom.nextBytes(iv)
    val ivSpec = IvParameterSpec(iv)


    // Performing encryption and storing the salt, encrypted password and the iv into the HashMap
    val password = str_password.toByteArray()
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding") // 1
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    val encrypted = cipher.doFinal(password) // 2

    val map = HashMap<String,Any>()
    map["salt"] = salt
    map["iv"] = iv
    map["encrypted"] = encrypted
    // The string returned contains the encrypted, salt that was used, and the ByteArray
    return map
}
/**
 * This is the new encryption_method that doesn't create a salt value and the 16 random byte,
 * but rather uses those that are given as argument
 * I have used it in the compare method to re-encrypt the user log in password to check whether it will match the one in the firebase
 */
fun encryption_method(str_password: String, salt: ByteArray, iv: ByteArray):String{
    val pwd = str_password.toCharArray()
    val pbKeySpec = PBEKeySpec(pwd, salt, 1324, 256) // 1
    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1") // 2
    val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded // 3
    val keySpec = SecretKeySpec(keyBytes, "AES") // 4

    // Packaged it into an IvParameterSpec object.
    val ivSpec = IvParameterSpec(iv)

    // Performing encryption and storing the salt, encrypted password and the iv into the HashMap
    val password = str_password.toByteArray()
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
    val encrypted = cipher.doFinal(password)

    // Return the ByteArray of the encryption
    return encrypted.toString()
}// End of the method

/**
 * Creating a method that reads the encrypted password from firebase
 */
fun getUserInfo(str_username: String): HashMap<String,Any>{
    var map = HashMap<String,Any>()
    val rootref = FirebaseDatabase.getInstance().reference
    val order_for_password = rootref.child("users")
    val valueEventListener = object : ValueEventListener{
        override fun onDataChange(p0: DataSnapshot) {
            for (data in p0.children){
                if(data.child("username").value == str_username){
                    map = data.child("password").value as HashMap<String, Any>
                    break
                }
            }
        }

        override fun onCancelled(p0: DatabaseError) {
            Log.d("EasyParkError", p0.message) // Reports the error
        }
    }
    order_for_password.addListenerForSingleValueEvent(valueEventListener)
    // returning the HashMap
    return map
}// End of the getUserInfo() method

/**
 * Compare the encrypted password with the user's correct password for log in
 * It returns true if the encrypted password matches the user password on log in
 * When the function compares and returns true, them the user can be logged in else, the user won't be allow to log in
 */
fun compare_passwords(username:String, pwd:String): Boolean{
    // Fetch data from Firebase, and get the hashed password associated with the given username
    // decrypt the associated password, to get the plaintext password
    // compare the two passwords, the decrypted and the given pwd on log in
    // return true if the passwords match else false
    // This method returns the HashMap that has the salt value, the 16 random Byte and the encrypted password
    val firebaseMap = getUserInfo(username)
    val encryptedValue = firebaseMap["encrypted"].toString()
    val saltValue = firebaseMap["salt"] as ByteArray
    val random16Byte = firebaseMap["iv"] as ByteArray

    if(encryption_method(pwd,saltValue,random16Byte) == encryptedValue){
        return true
    }
    // Or the format below can be use, where by the encrypted password in the firebase database is decrypted and,
    // compared with the log in password of the user to see if they match
    // call the decrypt_method
    //    if(decrypt_method(firebaseMap,pwd) == pwd){
    //        return true
    //    }

    return false
}

/**
 * First fetch data from the firebase using the above method
 * Decrypting the required password using the same methods as in encryption process.
 */
fun decrypt_method(decrypt_cipher: Map<String,Any>, pwd:String):String {
    val password = pwd.toCharArray()
    // Ths decrypt_cipher contains the encrypted_password, salt that was used during encryption, and the byteArray
    //regenerate key from password
    val salt = decrypt_cipher["salt"]
    val iv = decrypt_cipher["iv"]
    val encrypted = decrypt_cipher["encrypted"]
    val pbKeySpec = PBEKeySpec(password, salt as ByteArray?, 1324, 256)
    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
    val keySpec = SecretKeySpec(keyBytes, "AES")

    //Decrypt
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    val ivSpec = IvParameterSpec(iv as ByteArray?)
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
    val decrypted = cipher.doFinal(encrypted as ByteArray?)

    return decrypted.toString()
}