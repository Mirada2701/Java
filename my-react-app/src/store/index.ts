import { configureStore } from '@reduxjs/toolkit';
import {apiCategory} from "../services/apiCategory.ts";
import {productsApi} from "../services/productsApi.ts";
import {authApi} from "../services/authApi.ts";


export const store = configureStore({
    reducer: {
        [apiCategory.reducerPath]: apiCategory.reducer, // Додаємо API reducer
        [productsApi.reducerPath]: productsApi.reducer, // Додаємо API reducer
        [authApi.reducerPath]: authApi.reducer, // Додаємо API reducer
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(apiCategory.middleware, productsApi.middleware, authApi.middleware), // Додаємо API middleware
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;