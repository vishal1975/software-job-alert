package com.vishal.softwarejobalert.ModelClasses

data class User(val username: String? = null, val countryName: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}