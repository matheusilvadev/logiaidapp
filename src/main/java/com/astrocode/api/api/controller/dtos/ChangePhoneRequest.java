package com.astrocode.api.api.controller.dtos;

import com.astrocode.api.shared.vo.PhoneBR;

public record ChangePhoneRequest(PhonePayload phoneBR) {
    public record PhonePayload(String digits){
        public PhoneBR toDomain(){
            return PhoneBR.of(digits);
        }
    }
}
