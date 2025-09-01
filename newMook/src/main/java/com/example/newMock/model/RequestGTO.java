package com.example.newMock.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestGTO {
    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String closeDate;

}
