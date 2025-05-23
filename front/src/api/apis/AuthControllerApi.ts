/* tslint:disable */
/* eslint-disable */
/**
 * Api V1
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import * as runtime from '../runtime';
import type {
  AuthTokensResponseBody,
  ChangePasswordRequestBody,
  LoginRequestBody,
  RefreshTokenRequestBody,
  RegisterRequestBody,
} from '../models/index';
import {
    AuthTokensResponseBodyFromJSON,
    AuthTokensResponseBodyToJSON,
    ChangePasswordRequestBodyFromJSON,
    ChangePasswordRequestBodyToJSON,
    LoginRequestBodyFromJSON,
    LoginRequestBodyToJSON,
    RefreshTokenRequestBodyFromJSON,
    RefreshTokenRequestBodyToJSON,
    RegisterRequestBodyFromJSON,
    RegisterRequestBodyToJSON,
} from '../models/index';

export interface ChangePasswordRequest {
    changePasswordRequestBody: ChangePasswordRequestBody;
}

export interface LoginRequest {
    loginRequestBody: LoginRequestBody;
}

export interface RefreshTokenRequest {
    refreshTokenRequestBody: RefreshTokenRequestBody;
}

export interface RegisterRequest {
    registerRequestBody: RegisterRequestBody;
}

/**
 * 
 */
export class AuthControllerApi extends runtime.BaseAPI {

    /**
     */
    async changePasswordRaw(requestParameters: ChangePasswordRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<boolean>> {
        if (requestParameters['changePasswordRequestBody'] == null) {
            throw new runtime.RequiredError(
                'changePasswordRequestBody',
                'Required parameter "changePasswordRequestBody" was null or undefined when calling changePassword().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        if (this.configuration && (this.configuration.username !== undefined || this.configuration.password !== undefined)) {
            headerParameters["Authorization"] = "Basic " + btoa(this.configuration.username + ":" + this.configuration.password);
        }
        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/auth/change-password`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: ChangePasswordRequestBodyToJSON(requestParameters['changePasswordRequestBody']),
        }, initOverrides);

        if (this.isJsonMime(response.headers.get('content-type'))) {
            return new runtime.JSONApiResponse<boolean>(response);
        } else {
            return new runtime.TextApiResponse(response) as any;
        }
    }

    /**
     */
    async changePassword(requestParameters: ChangePasswordRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<boolean> {
        const response = await this.changePasswordRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async loginRaw(requestParameters: LoginRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<AuthTokensResponseBody>> {
        if (requestParameters['loginRequestBody'] == null) {
            throw new runtime.RequiredError(
                'loginRequestBody',
                'Required parameter "loginRequestBody" was null or undefined when calling login().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        if (this.configuration && (this.configuration.username !== undefined || this.configuration.password !== undefined)) {
            headerParameters["Authorization"] = "Basic " + btoa(this.configuration.username + ":" + this.configuration.password);
        }
        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/auth/login`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: LoginRequestBodyToJSON(requestParameters['loginRequestBody']),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => AuthTokensResponseBodyFromJSON(jsonValue));
    }

    /**
     */
    async login(requestParameters: LoginRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<AuthTokensResponseBody> {
        const response = await this.loginRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async refreshTokenRaw(requestParameters: RefreshTokenRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<AuthTokensResponseBody>> {
        if (requestParameters['refreshTokenRequestBody'] == null) {
            throw new runtime.RequiredError(
                'refreshTokenRequestBody',
                'Required parameter "refreshTokenRequestBody" was null or undefined when calling refreshToken().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        if (this.configuration && (this.configuration.username !== undefined || this.configuration.password !== undefined)) {
            headerParameters["Authorization"] = "Basic " + btoa(this.configuration.username + ":" + this.configuration.password);
        }
        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/auth/refresh`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: RefreshTokenRequestBodyToJSON(requestParameters['refreshTokenRequestBody']),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => AuthTokensResponseBodyFromJSON(jsonValue));
    }

    /**
     */
    async refreshToken(requestParameters: RefreshTokenRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<AuthTokensResponseBody> {
        const response = await this.refreshTokenRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async registerRaw(requestParameters: RegisterRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<string>> {
        if (requestParameters['registerRequestBody'] == null) {
            throw new runtime.RequiredError(
                'registerRequestBody',
                'Required parameter "registerRequestBody" was null or undefined when calling register().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        if (this.configuration && (this.configuration.username !== undefined || this.configuration.password !== undefined)) {
            headerParameters["Authorization"] = "Basic " + btoa(this.configuration.username + ":" + this.configuration.password);
        }
        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/auth/register`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: RegisterRequestBodyToJSON(requestParameters['registerRequestBody']),
        }, initOverrides);

        if (this.isJsonMime(response.headers.get('content-type'))) {
            return new runtime.JSONApiResponse<string>(response);
        } else {
            return new runtime.TextApiResponse(response) as any;
        }
    }

    /**
     */
    async register(requestParameters: RegisterRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<string> {
        const response = await this.registerRaw(requestParameters, initOverrides);
        return await response.value();
    }

}
