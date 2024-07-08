function loadFavourite() {
    const select_number = document.querySelector('.ajax-favourite__number');
    const liked_all = document.querySelectorAll('.like-btn--liked').length;
    console.log('Liked all:', liked_all);
    select_number.innerHTML = liked_all.toString()
}
document.addEventListener('DOMContentLoaded', function() {
    loadFavourite();
});
$(document).ready(function() {
    console.log('Ajax script loaded');
    $('.ajax-favourite__btn').on('click', function() {
        const button = $(this); // Assign this to button
        const productId = button.data('product-id');
        const csrfToken = $('meta[name="csrf-token"]').attr('content'); // Lấy CSRF token

        if (productId) {
            $.ajax({
                url: '/favourite/toggle',
                method: 'POST',
                data: { productId: productId },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
                },
                success: function(response) {
                    if (response === 'success') {
                        if (button.hasClass('like-btn--liked')) {
                            button.removeClass('like-btn--liked');
                            createToast('success', 'fa-solid fa-circle-check', 'Yêu thích', 'Đã xóa sản phẩm khỏi danh sách yêu thích.');
                        } else {
                            button.addClass('like-btn--liked');
                            createToast('success', 'fa-solid fa-circle-check', 'Yêu thích', 'Đã thêm sản phẩm vào danh sách yêu thích.');
                        }
                        loadFavourite();
                        console.log('Toggled favourite product:', response);
                    } else {
                        createToast('warning', 'fa-solid fa-circle-exclamation', 'Yêu cầu', 'Vui lòng đăng nhập để thêm sản phẩm vào danh sách yêu thích.');
                        console.log('Redirect to login page');
                    }
                },
                error: function(xhr, status, error) {
                    createToast('error', 'fa-solid fa-circle-xmark', 'Lỗi', 'Đã xảy ra lỗi khi thêm sản phẩm vào danh sách yêu thích.');
                    console.log('Error:', error);
                }
            });
        } else {
            console.log('ProductId is not present.');
        }
    });
});