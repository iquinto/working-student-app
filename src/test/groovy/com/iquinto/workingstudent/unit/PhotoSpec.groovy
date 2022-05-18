package com.iquinto.workingstudent.unit

import com.iquinto.workingstudent.model.Photo
import spock.lang.Specification

class PhotoSpec extends Specification {

    def "test creat new photo object"(){
        when:
        Photo photo = new Photo("test.pdf", "application/pdf","some content".bytes)

        then:
        photo != null
        photo.getName() == "test.pdf"
    }

    def "test creat new photo object 1"(){
        when:
        Photo photo = new Photo()
        photo.setId('xxxx' as String)
        photo.setName("test.pdf" )
        photo.setType("application/pdf")
        photo.setData( "some content".bytes)

        then:
        photo != null
        photo.getName() == "test.pdf"
    }
}
