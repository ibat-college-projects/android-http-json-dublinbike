package com.example.httpjsondemo1

class Video( val id: Int, val name: String)

class HomeFeed(val videos: List<Video>)

class Bike (val number: Int, val name: String, val available_bikes: Int )

class Stations(val stations: List<Bike>)