package com.mtech.ique.queueservice.model.enums;

public enum TicketStatus {
  PENDING("pending"),
  SKIPPED("skipped"),
  SEATED("seated");

  private final String status;

  TicketStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return status;
  }
}
