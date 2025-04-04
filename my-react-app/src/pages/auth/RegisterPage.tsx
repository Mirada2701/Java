import React, { useState } from "react";
import { Button, Form, Input } from "antd";
import { IUserRegisterRequest } from "./types.ts";
import { useNavigate } from "react-router-dom";
import { useRegisterUserMutation } from "../../services/authApi.ts";
import ImageModal from "../../components/ImageModal";

const { Item } = Form;

const RegisterPage: React.FC = () => {
    const [form] = Form.useForm<IUserRegisterRequest>();
    const navigate = useNavigate();
    const [registerUser] = useRegisterUserMutation();

    const [croppedImage, setCroppedImage] = useState<string | null>(null);

    const onFinish = async (values: IUserRegisterRequest) => {
        try {
            values.image = croppedImage;
            const response = await registerUser(values).unwrap();
            console.log("Користувача успішно зареєстровано", response);
            navigate("..");
        } catch (error) {
            console.error("Помилка при реєстрації", error);
        }
    };

    return (
        <>
            <h1 className="text-center text-4xl font-bold text-blue-500">
                Реєстрація на сайті
            </h1>

            <div style={{ maxWidth: "400px", margin: "0 auto" }}>
                <Form form={form} onFinish={onFinish} layout="vertical">
                    <Item
                        name="username"
                        label={"Електронна пошта"}
                        rules={[
                            { required: true, message: "Вкажіть свою пошту" },
                            { type: "email", message: "Введіть коректний email" },
                        ]}
                    >
                        <Input placeholder={"Електронна пошта"} />
                    </Item>

                    {/* Попередній перегляд зображення */}
                    {croppedImage && (
                        <div className="mb-4 text-center">
                            <h3 className="text-sm text-gray-700 mb-1">Обране фото:</h3>
                            <img
                                src={croppedImage}
                                alt="Preview"
                                className="w-24 h-24 object-cover rounded-full mx-auto border shadow"
                            />
                        </div>
                    )}

                    {/* Кнопка модалки вибору фото */}
                    <ImageModal onImageCropped={(base64) => setCroppedImage(base64)} />

                    <Item
                        name="password"
                        label="Пароль"
                        rules={[
                            { required: true, message: "Введіть пароль" },
                            { min: 6, message: "Пароль має містити щонайменше 6 символів" },
                        ]}
                    >
                        <Input.Password placeholder="Введіть пароль" />
                    </Item>

                    <Item
                        name="confirmPassword"
                        label="Підтвердження паролю"
                        dependencies={["password"]}
                        rules={[
                            { required: true, message: "Підтвердіть пароль" },
                            ({ getFieldValue }) => ({
                                validator(_, value) {
                                    if (!value || getFieldValue("password") === value) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject(new Error("Паролі не співпадають"));
                                },
                            }),
                        ]}
                    >
                        <Input.Password placeholder="Повторіть пароль" />
                    </Item>

                    <Item>
                        <Button type="primary" htmlType="submit" className="w-full">
                            Реєстрація
                        </Button>
                    </Item>
                </Form>
            </div>
        </>
    );
};

export default RegisterPage;
