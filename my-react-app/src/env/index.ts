const REMOTE_BASE_URL: string = import.meta.env.VITE_BASE_URL;
const REMOTE_BASE_API: string = REMOTE_BASE_URL + "/api/";
const REMOTE_IMAGES_URL: string = REMOTE_BASE_URL + "/images/";
const ACCESS_KEY: string = import.meta.env.VITE_APP_ACCESS_KEY;

const APP_ENV = {
    REMOTE_BASE_URL,
    REMOTE_BASE_API,
    REMOTE_IMAGES_URL,
    ACCESS_KEY,
}

export { APP_ENV };