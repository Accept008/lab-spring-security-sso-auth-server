package com.example.lab.base.response;

import org.springframework.http.HttpStatus;

public interface StatusSupportResponse extends Response {

    /**
     * Get http status code
     *
     * @return http status code
     */
    int getStatus();

    /**
     * Set http status code
     *
     * @param status the http status code int value to set
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric value.
     */
    void setStatus(int status) throws IllegalArgumentException;

    /**
     * Set http status code
     *
     * @param status the http status code to set
     */
    void setStatus(HttpStatus status);

}
