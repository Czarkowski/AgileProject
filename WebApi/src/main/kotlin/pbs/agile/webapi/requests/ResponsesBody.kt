package pbs.agile.webapi.requests

import pbs.agile.webapi.dtos.LoggedUserDto

data class AuthTokensResponseBody(val token: String, val refreshToken: String? = null, val loggedUser: LoggedUserDto? = null)
