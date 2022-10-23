package com.mtech.ique.queueservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectNotification {
  public String target;
  public String title;
  public String message;
}
