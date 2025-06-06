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

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface UserUpdateRequestBody
 */
export interface UserUpdateRequestBody {
    /**
     * 
     * @type {string}
     * @memberof UserUpdateRequestBody
     */
    firstName: string;
    /**
     * 
     * @type {string}
     * @memberof UserUpdateRequestBody
     */
    lastName: string;
}

/**
 * Check if a given object implements the UserUpdateRequestBody interface.
 */
export function instanceOfUserUpdateRequestBody(value: object): value is UserUpdateRequestBody {
    if (!('firstName' in value) || value['firstName'] === undefined) return false;
    if (!('lastName' in value) || value['lastName'] === undefined) return false;
    return true;
}

export function UserUpdateRequestBodyFromJSON(json: any): UserUpdateRequestBody {
    return UserUpdateRequestBodyFromJSONTyped(json, false);
}

export function UserUpdateRequestBodyFromJSONTyped(json: any, ignoreDiscriminator: boolean): UserUpdateRequestBody {
    if (json == null) {
        return json;
    }
    return {
        
        'firstName': json['firstName'],
        'lastName': json['lastName'],
    };
}

export function UserUpdateRequestBodyToJSON(json: any): UserUpdateRequestBody {
    return UserUpdateRequestBodyToJSONTyped(json, false);
}

export function UserUpdateRequestBodyToJSONTyped(value?: UserUpdateRequestBody | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'firstName': value['firstName'],
        'lastName': value['lastName'],
    };
}

