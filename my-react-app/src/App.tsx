import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import AboutPage from "./pages/AboutPage";
import ContactPage from "./pages/ContactPage";
import CategoryListPage from "./pages/Category/CategoryListPage.tsx";
import CategoryCreatePage from "./pages/Category/CategoryCreatePage.tsx";
import EditCategoryPage from "./pages/Category/EditCategoryPage.tsx";
import ProductListPage from "./pages/Product/ProductListPage.tsx";
import CreateProductPage from "./pages/Product/CreateProductPage.tsx";
import EditProductPage from "./pages/Product/EditProductPage.tsx";

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<HomePage />} />
                    <Route path="about" element={<AboutPage />} />
                    <Route path="contact" element={<ContactPage />} />
                    <Route path="categories">
                        <Route index element={<CategoryListPage />} />
                        <Route path="create" element={<CategoryCreatePage />} />
                        <Route path="edit/:id" element={<EditCategoryPage />} />
                    </Route>

                    <Route path="products">
                        <Route index element={<ProductListPage />} />
                        <Route path="create" element={<CreateProductPage />} />
                        <Route path="edit/:id" element={<EditProductPage />} />
                    </Route>
                </Route>
            </Routes>
        </Router>
    );
};

export default App;