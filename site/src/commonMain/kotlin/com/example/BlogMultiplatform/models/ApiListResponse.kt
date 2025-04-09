package com.example.BlogMultiplatform.models

expect sealed  class ApiListResponse {

    object Idle
    class Success
    class Error

}