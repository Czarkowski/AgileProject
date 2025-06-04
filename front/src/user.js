import { Configuration } from '@/api';
import { AuthControllerApi } from "@/api";

export function getLoggedUser() {
    const loggedUser = localStorage.getItem('loggedUser');
    if (!loggedUser) return null;
    return JSON.parse(loggedUser);

}

export async function refreshTokenIfNeeded() {
    const loggedUser = getLoggedUser();
    if (!loggedUser) return null;

    const token = loggedUser.token;
    const refreshToken = loggedUser.refreshToken;

    if (!token || !refreshToken) return null;

    function isTokenExpired(token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const now = Math.floor(Date.now() / 1000);
            return payload.exp < now;
        } catch {
            return true;
        }
    }

    if (!isTokenExpired(token)) {
        return loggedUser;
    }

    try {
        const config = new Configuration();
        const authApi = new AuthControllerApi(config);

        const data = await authApi.refreshToken({ refreshTokenRequestBody: { refreshToken } });

        localStorage.setItem('loggedUser', JSON.stringify({
            token: data.token,
            refreshToken: data.refreshToken,
            loggedUser: data.loggedUser,
        }));

    } catch {
        localStorage.removeItem('loggedUser');
        console.error("refreshtoken error");
    }

}

export function logout() {
    localStorage.removeItem('loggedUser');
}

