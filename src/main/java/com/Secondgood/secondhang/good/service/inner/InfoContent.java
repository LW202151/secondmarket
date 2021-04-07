package com.Secondgood.secondhang.good.service.inner;

import com.Secondgood.secondhang.good.entity.AddressEntity;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Data

public class InfoContent {

    private String name;

    private String phone;

    private String password ;

    List<AddressEntity> adress;

}
