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
  ProjectAddRequestBody,
  ProjectDto,
  ProjectUpdateRequestBody,
  UserAndProjectRequestBody,
} from '../models/index';
import {
    ProjectAddRequestBodyFromJSON,
    ProjectAddRequestBodyToJSON,
    ProjectDtoFromJSON,
    ProjectDtoToJSON,
    ProjectUpdateRequestBodyFromJSON,
    ProjectUpdateRequestBodyToJSON,
    UserAndProjectRequestBodyFromJSON,
    UserAndProjectRequestBodyToJSON,
} from '../models/index';

export interface AddProjectRequest {
    projectAddRequestBody: ProjectAddRequestBody;
}

export interface AddUserToProjectRequest {
    userAndProjectRequestBody: UserAndProjectRequestBody;
}

export interface DeleteUserFromProjectRequest {
    userAndProjectRequestBody: UserAndProjectRequestBody;
}

export interface UpdateProjectRequest {
    projectId: number;
    projectUpdateRequestBody: ProjectUpdateRequestBody;
}

/**
 * 
 */
export class ProjectControllerApi extends runtime.BaseAPI {

    /**
     */
    async addProjectRaw(requestParameters: AddProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ProjectDto>> {
        if (requestParameters['projectAddRequestBody'] == null) {
            throw new runtime.RequiredError(
                'projectAddRequestBody',
                'Required parameter "projectAddRequestBody" was null or undefined when calling addProject().'
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
            path: `/api/projects`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: ProjectAddRequestBodyToJSON(requestParameters['projectAddRequestBody']),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ProjectDtoFromJSON(jsonValue));
    }

    /**
     */
    async addProject(requestParameters: AddProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ProjectDto> {
        const response = await this.addProjectRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async addUserToProjectRaw(requestParameters: AddUserToProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<object>> {
        if (requestParameters['userAndProjectRequestBody'] == null) {
            throw new runtime.RequiredError(
                'userAndProjectRequestBody',
                'Required parameter "userAndProjectRequestBody" was null or undefined when calling addUserToProject().'
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
            path: `/api/projects/addUser`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: UserAndProjectRequestBodyToJSON(requestParameters['userAndProjectRequestBody']),
        }, initOverrides);

        return new runtime.JSONApiResponse<any>(response);
    }

    /**
     */
    async addUserToProject(requestParameters: AddUserToProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<object> {
        const response = await this.addUserToProjectRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async deleteUserFromProjectRaw(requestParameters: DeleteUserFromProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<object>> {
        if (requestParameters['userAndProjectRequestBody'] == null) {
            throw new runtime.RequiredError(
                'userAndProjectRequestBody',
                'Required parameter "userAndProjectRequestBody" was null or undefined when calling deleteUserFromProject().'
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
            path: `/api/projects/removeUser`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: UserAndProjectRequestBodyToJSON(requestParameters['userAndProjectRequestBody']),
        }, initOverrides);

        return new runtime.JSONApiResponse<any>(response);
    }

    /**
     */
    async deleteUserFromProject(requestParameters: DeleteUserFromProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<object> {
        const response = await this.deleteUserFromProjectRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async getAllProjectsRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<ProjectDto>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

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
            path: `/api/projects`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(ProjectDtoFromJSON));
    }

    /**
     */
    async getAllProjects(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<ProjectDto>> {
        const response = await this.getAllProjectsRaw(initOverrides);
        return await response.value();
    }

    /**
     */
    async updateProjectRaw(requestParameters: UpdateProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<object>> {
        if (requestParameters['projectId'] == null) {
            throw new runtime.RequiredError(
                'projectId',
                'Required parameter "projectId" was null or undefined when calling updateProject().'
            );
        }

        if (requestParameters['projectUpdateRequestBody'] == null) {
            throw new runtime.RequiredError(
                'projectUpdateRequestBody',
                'Required parameter "projectUpdateRequestBody" was null or undefined when calling updateProject().'
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
            path: `/api/projects/{projectId}`.replace(`{${"projectId"}}`, encodeURIComponent(String(requestParameters['projectId']))),
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: ProjectUpdateRequestBodyToJSON(requestParameters['projectUpdateRequestBody']),
        }, initOverrides);

        return new runtime.JSONApiResponse<any>(response);
    }

    /**
     */
    async updateProject(requestParameters: UpdateProjectRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<object> {
        const response = await this.updateProjectRaw(requestParameters, initOverrides);
        return await response.value();
    }

}
