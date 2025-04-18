import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import {IProduct, IProductCreate, IProductEdit} from "../pages/Product/types";
import { APP_ENV } from "../env";
import {serialize} from "object-to-formdata";

export const productsApi = createApi({
    reducerPath: 'productsApi',
    baseQuery: fetchBaseQuery({ baseUrl: `${APP_ENV.REMOTE_BASE_API}` }),
    tagTypes: ["Product"], // Додаємо tag для категорій
    endpoints: (builder) => ({

        getAllProducts: builder.query<IProduct[], void>({
            query: () => 'products',
            providesTags: ["Product"]  // Позначаємо, що цей запит пов'язаний з "Product"
        }),

        getProductById: builder.query<IProduct, string>({
            query: (id) => `products/${id}`,
            providesTags: ["Product"]
        }),
        createProduct: builder.mutation<IProduct, IProductCreate>({
            query: (model) => {
                try {
                    const formData = serialize(model);
                    return {
                        url: 'products',
                        method: 'POST',
                        body: formData
                    };
                } catch {
                    throw new Error("Error serializing the form data.");
                }
            },
            invalidatesTags: ["Product"]   // Інвалідовуємо "Product" після створення
        }),
        editProduct: builder.mutation<void, IProductEdit>({
            query: (model) => {
                try {
                    const formData = serialize(model);
                    return {
                        url: `products/${model.id}`,
                        method: 'PUT',
                        body: formData
                    };
                } catch {
                    throw new Error("Error serializing the form data.");
                }
            },
            invalidatesTags: ["Product"]   // Інвалідовуємо "Product" після оновлення
        }),

        deleteProduct: builder.mutation<void, number>({
            query: (id) => ({
                url: `products/${id}`,
                method: 'DELETE'
            }),
            invalidatesTags: ["Product"]
        }),
    }),
});

export const {
    useGetAllProductsQuery,
    useGetProductByIdQuery,
    useCreateProductMutation,
    useEditProductMutation,
    useDeleteProductMutation
} = productsApi;