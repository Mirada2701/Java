'use client';

import { useRef, useState } from 'react';
import { Dialog, DialogBackdrop, DialogPanel, DialogTitle } from '@headlessui/react';
import Cropper from 'cropperjs';
import 'cropperjs/dist/cropper.css';

export default function ImageModal({
                                       onImageCropped,
                                   }: {
    onImageCropped: (base64: string) => void;
}) {
    const [open, setOpen] = useState(false);
    const imageRef = useRef<HTMLImageElement>(null);
    const cropperRef = useRef<Cropper | null>(null);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files.length > 0) {
            const file = e.target.files[0];
            const imageURL = URL.createObjectURL(file);

            setOpen(true);

            setTimeout(() => {
                if (imageRef.current) {
                    imageRef.current.src = imageURL;

                    // Destroy old cropper if it exists
                    if (cropperRef.current) {
                        cropperRef.current.destroy();
                    }

                    cropperRef.current = new Cropper(imageRef.current, {
                        aspectRatio: 1,
                        viewMode: 1,
                        zoomable: true,
                        scalable: false,
                        movable: false,
                    });
                }
            }, 100); // невелика затримка для оновлення DOM
        }
    };

    const cropImage = () => {
        if (cropperRef.current) {
            const canvas = cropperRef.current.getCroppedCanvas();
            if (canvas) {
                const base64 = canvas.toDataURL('image/png');
                onImageCropped(base64);
            }
        }
    };

    const closeModal = () => {
        if (cropperRef.current) {
            cropperRef.current.destroy();
            cropperRef.current = null;
        }
        setOpen(false);
    };

    return (
        <>
            {/* Кнопка вибору файлу */}
            <label
                htmlFor="fileSelect"
                className="cursor-pointer text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
            >
                Обрати фото
            </label>
            <input id="fileSelect" type="file" accept="image/*" style={{ display: 'none' }} onChange={handleFileChange} />

            {/* Модалка */}
            <Dialog open={open} onClose={closeModal} className="relative z-10">
                <DialogBackdrop className="fixed inset-0 bg-black/50" />

                <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                    <div className="flex min-h-full items-center justify-center p-4">
                        <DialogPanel className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all w-full max-w-lg">
                            <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                <DialogTitle as="h3" className="text-lg font-medium text-gray-900 mb-4">
                                    Обрізати фото
                                </DialogTitle>

                                <div className="border rounded-md p-2 max-h-[400px] overflow-auto">
                                    <img ref={imageRef} alt="Preview" className="max-w-full" />
                                </div>
                            </div>

                            {/* Кнопки дій */}
                            <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                <button
                                    type="button"
                                    onClick={() => {
                                        cropImage();
                                        closeModal();
                                    }}
                                    className="inline-flex w-full justify-center rounded-md bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-500 sm:ml-3 sm:w-auto"
                                >
                                    Зберегти
                                </button>
                                <button
                                    type="button"
                                    onClick={closeModal}
                                    className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                >
                                    Скасувати
                                </button>
                            </div>
                        </DialogPanel>
                    </div>
                </div>
            </Dialog>
        </>
    );
}
