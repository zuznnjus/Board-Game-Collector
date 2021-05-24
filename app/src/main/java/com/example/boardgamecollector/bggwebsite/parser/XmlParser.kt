package com.example.boardgamecollector.bggwebsite.parser

import java.io.InputStream

interface XmlParser<T>  {
    fun parse(inputStream: InputStream): T
}