package me.kansio.indium.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<X, Y> {

     public X x;
     public Y y;

    public Pair() {}

}
