package com.mtech.ique.queueservice.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
  Long userId;
  String token;
}
