import React from "react";
import { useSelector } from "react-redux";
import { getUser, logOut } from "../store/slices/userSlice.ts";
import { useAppDispatch } from "../store";
import { useNavigate } from "react-router-dom";

const Header: React.FC = () => {
    const user = useSelector(getUser);
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    return (
        <header className="bg-white shadow p-4 flex justify-between items-center">
            <h1 className="text-xl font-bold text-gray-800">My Website</h1>
            <nav>
                <ul className="flex gap-4 items-center">
                    <li>
                        <a href="/" className="text-gray-600 hover:text-blue-500">
                            Home
                        </a>
                    </li>
                    <li>
                        <a href="/categories" className="text-gray-600 hover:text-blue-500">
                            Категорії
                        </a>
                    </li>
                    <li>
                        <a href="/about" className="text-gray-600 hover:text-blue-500">
                            About
                        </a>
                    </li>
                    <li>
                        <a href="/contact" className="text-gray-600 hover:text-blue-500">
                            Contact
                        </a>
                    </li>

                    {user ? (
                        <>
                            <li className="flex items-center gap-2">
                                {user.image && (
                                    <img
                                        src={user.image}
                                        alt="avatar"
                                        className="w-8 h-8 rounded-full border object-cover"
                                    />
                                )}
                                <a href="/profile" className="text-gray-600 hover:text-blue-500">
                                    {user.email}
                                </a>
                            </li>
                            <li>
                                <a
                                    href="#"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        dispatch(logOut());
                                        navigate("/");
                                    }}
                                    className="text-gray-600 hover:text-blue-500"
                                >
                                    Вихід
                                </a>
                            </li>
                        </>
                    ) : (
                        <>
                            <li>
                                <a href="/login" className="text-gray-600 hover:text-blue-500">
                                    Вхід
                                </a>
                            </li>
                            <li>
                                <a href="/register" className="text-gray-600 hover:text-blue-500">
                                    Реєстрація
                                </a>
                            </li>
                        </>
                    )}
                </ul>
            </nav>
        </header>
    );
};

export default Header;
