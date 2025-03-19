import React from "react";
import {Button, Form, Input} from "antd";
import {GoogleOAuthProvider} from "@react-oauth/google";
import { GoogleOutlined } from '@ant-design/icons';
import GoogleLoginButton from "./GoogleLoginButton.tsx";
import {IUserLoginRequest} from "./types.ts";
import {useGoogleLoginUserMutation, useLoginUserMutation} from "../../services/authApi.ts";
import {useNavigate} from "react-router-dom";


const {Item} = Form;

const LoginPage: React.FC = () => {

    const [form] = Form.useForm<IUserLoginRequest>();
    const navigate = useNavigate();
    const [googleLoginUser] = useGoogleLoginUserMutation();
    const [loginUser] = useLoginUserMutation();

    const onFinish = async (values: IUserLoginRequest) => {

        console.log("Login user", values);
        const response = await loginUser(values).unwrap();
        console.log("Користувач успішно ввійшов ", response);
        navigate("..");
    }

    const onLoginGoogleResult = async (tokenGoogle:string) => {
        try {
            console.log("Login user", tokenGoogle);
            const response = await googleLoginUser({token: tokenGoogle}).unwrap();
            console.log("Користувач успішно ввійшов через гугл", response);
            navigate("..");
        } catch (error) {
            console.error("Помилка при вході через гугл", error);
        }
    }

    return (
        <>
            <GoogleOAuthProvider clientId={"105669341684-vgu1rd2m41s3bpsvnd3biksmj3ag9ccf.apps.googleusercontent.com"}>
                <h1 className={"text-center text-4xl font-bold text-blue-500"}>Вхід на сайт</h1>

                <div style={{maxWidth: '400px', margin: '0 auto'}}>
                    <Form
                        form={form}
                        onFinish={onFinish}
                        layout="vertical">

                        <Item
                            name="username"
                            label={"Електронна пошта"}
                            rules={[
                                {required: true, message: "Вкажіть свою пошшту"},
                                {type: "email", message: "Введіть коректний email"}
                            ]}>
                            <Input placeholder={"Електронна пошта"}/>
                        </Item>

                        <Item
                            name="password"
                            label="Пароль"
                            rules={[
                                {required: true, message: "Введіть пароль"},
                                {min: 6, message: "Пароль має містити щонайменше 6 символів"}
                            ]}
                        >
                            <Input.Password placeholder="Введіть пароль"/>
                        </Item>


                        <Item>
                            <Button type="primary" htmlType="submit">
                                Вхід
                            </Button>
                        </Item>

                        <GoogleLoginButton icon={<GoogleOutlined />} title='Увійти з Google' onLogin={onLoginGoogleResult} />

                    </Form>
                </div>
            </GoogleOAuthProvider>
        </>
    )
}

export default LoginPage;
