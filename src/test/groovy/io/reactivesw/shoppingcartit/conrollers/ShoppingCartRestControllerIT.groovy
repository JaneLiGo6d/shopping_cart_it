package io.reactivesw.shoppingcartit.conrollers

import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

/**
 * Created by janeli on 10/27/16.
 */
@WebAppConfiguration
@SpringBootTest
class ShoppingCartRestControllerIT extends Specification {

    def template = new TestRestTemplate();

    def "add sku to shopping cart"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart/add?skuId=1", String.class)
        ResponseEntity response1 = template.postForEntity("http://localhost:8088/api/shoppingcart/add?skuId=1", null, String.class)
        ResponseEntity response2 = template.postForEntity("http://localhost:8088/api/shoppingcart/add?skuId=1&quantity=2", null, String.class)

        expect:
        println response1.statusCodeValue
        gotValue == "{\"result\":true}"
        response1.statusCodeValue == 200
        response2.statusCodeValue == 200
    }

    def "add sku to shopping cart error"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart/add?skuId=0", String.class)
        ResponseEntity response = template.postForEntity("http://localhost:8088/api/shoppingcart/add", null, String.class)

        expect:
        print gotValue
        println response.statusCodeValue
        response.statusCodeValue == 400
        gotValue == "{\"result\":false}"
    }

    def "edit sku of shopping cart"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart/edit?skuId=1", String.class)
        ResponseEntity response1 = template.postForEntity("http://localhost:8088/api/shoppingcart/edit?skuId=1", null, String.class)
        ResponseEntity response2 = template.postForEntity("http://localhost:8088/api/shoppingcart/edit?skuId=1&quantity=2", null, String.class)

        expect:
        println response1.statusCodeValue
        gotValue == "{\"result\":true}"
        response1.statusCodeValue == 200
        response2.statusCodeValue == 200
    }

    def "edit sku of shopping cart error"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart/edit?skuId=0", String.class)
        ResponseEntity response = template.postForEntity("http://localhost:8088/api/shoppingcart/edit", null, String.class)

        expect:
        print gotValue
        println response.statusCodeValue
        response.statusCodeValue == 400
        gotValue == "{\"result\":false}"
    }

    def "list shopping cart"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart", String.class)

        expect:
        gotValue.size() > 0
    }

    def "delete sku from shopping cart"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart/delete/1", String.class)
        def gotFalse = template.getForObject("http://localhost:8088/api/shoppingcart/delete/0", String.class)

        expect:
        gotValue == "{\"result\":true}"
        gotFalse == "{\"result\":true}"
    }

    def "delete sku from shopping cart error"() {
        setup:
        def gotValue = template.getForEntity("http://localhost:8088/api/shoppingcart/delete/aaa", null, String.class)

        expect:
        gotValue.statusCodeValue == 400
    }

    def "delete shopping cart"() {
        setup:
        def gotValue = template.getForObject("http://localhost:8088/api/shoppingcart/delete", String.class)

        expect:
        gotValue == "{\"result\":true}"
    }
}
