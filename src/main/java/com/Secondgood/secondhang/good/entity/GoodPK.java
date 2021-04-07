package com.Secondgood.secondhang.good.entity;

import lombok.AllArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

public class GoodPK implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer g_id;

    private String goodsid;

    public GoodPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodPK)) return false;
        GoodPK goodPK = (GoodPK) o;
        return Objects.equals(g_id, goodPK.g_id) &&
                Objects.equals(goodsid, goodPK.goodsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(g_id, goodsid);
    }
}
