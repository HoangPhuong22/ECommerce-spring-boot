function loadFavourite() {
    const select_number = document.querySelector('.ajax-favourite__number');
    const liked_all = document.querySelectorAll('.like-btn--liked').length;
    select_number.innerHTML = liked_all.toString()
}
$(document).ready(function() {
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

// Thêm vào giỏ hàng
$(document).ready(function() {
    const selectedOption = $(this).find('option:selected');
    let quantity = 1,  productId = selectedOption.data('productid');

    $('#quantity-cart').on('change', function() {
        const selectedOption = $(this).find('option:selected');
        quantity = selectedOption.data('quantity');
    });

    $('#product-detail').on('change', function() {
        const selectedOption = $(this).find('option:selected');
        productId = selectedOption.data('productid');
    });
    $('#ajax-cart__add').on('click', function() {
        addCart(quantity, productId);
    });
});
function addCart(quantity, productId, isAdd = true) {
    console.log('Adding product to cart:', productId, quantity);
    const csrfToken = $('meta[name="csrf-token"]').attr('content'); // Lấy CSRF token
    $.ajax({
        url: '/cart/add',
        method: 'POST',
        data: {
            productDetailId: productId,
            quantity: quantity,
            isAdd: isAdd
        },
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
        },
        success: function(response) {
            if(!isNaN(response)) {
                if (response > 0) {
                    createToast('success', 'fa-solid fa-circle-check', 'Giỏ hàng', 'Đã thêm sản phẩm vào giỏ hàng.');
                } else {
                    createToast('warning', 'fa-solid fa-circle-exclamation', 'Giỏ hàng', 'Vượt quá số lượng tồn kho.');
                }
            } else {
                createToast('warning', 'fa-solid fa-circle-exclamation', 'Yêu cầu', 'Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.');
            }
        },
        error: function(xhr, status, error) {
            console.log('Error adding product to cart:', error);
            createToast('error', 'fa-solid fa-circle-xmark', 'Lỗi', 'Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng.');
        }
    });
}