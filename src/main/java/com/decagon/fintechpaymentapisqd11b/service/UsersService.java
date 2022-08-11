package com.decagon.fintechpaymentapisqd11b.service;

;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UsersService {



    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException;

}
