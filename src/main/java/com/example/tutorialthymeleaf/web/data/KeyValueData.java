package com.example.tutorialthymeleaf.web.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Iehor Funtusov, created 02/01/2021 - 4:38 AM
 */

@Getter
@Setter
@ToString
public class KeyValueData<K, V> {

    K key;
    V value;

    public KeyValueData(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
