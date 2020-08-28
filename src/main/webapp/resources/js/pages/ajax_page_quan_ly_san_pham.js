//cú phấp để đảm bảo tất cả các phần tử đã được load xong
// để có thể thực hiện thao tác lên nó

//B1 định nghĩa các thành phần cần phải thao tác
let selectSearchDanhMuc, selectSearchSapXep, textSearchTen, numberSearchGia, numberSearchDaBan, dateSearchNgayTao, selectSearchConHang, btnTimKiem, tableDuLieu, textTen, selectDanhMuc, numberGia, numberDaBan, numberBaoHanh, numberKhuyenMai, fileAnh, dateNgayTao, textareaGioiThieu, textareaThongSo, checkboxHetHang, btnLuuLai, btnXacNhanXoa;
let indexProduct, elementProduct;
let listProduct = [
    {
        id: 1,
        name: "Iphone 12",
        price: 10000,
        createDate: "2020-08-20",
        image: "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/thumbnail/300x/9df78eab33525d08d6e5fb8d27136e95/i/p/iphone11-purple-select-2019.png",
        introduction: "Iphone tai thỏ",
        specification: "Chip A11",
        soldOut: true,
        guarantee: 12,
        categoryId: 1,
        bouth: 1000,
        promotion: 10
    },
    {
        id: 2,
        name: "Iphone 11",
        price: 10000,
        createDate: "2020-08-20",
        image: "https://cdn.tgdd.vn/Products/Images/42/200533/iphone-11-pro-max-green-600x600.jpg",
        introduction: "Iphone tai thỏ",
        specification: "Chip A11",
        soldOut: false,
        guarantee: 12,
        categoryId: 2,
        bouth: 1000,
        promotion: 10
    },
];
$(function() {
    selectSearchDanhMuc = $("#select-search-danh-muc");
    selectSearchSapXep = $("#select-search-sap-xep");
    textSearchTen = $("#text-search-ten");
    numberSearchGia = $("#number-search-gia");
    numberSearchDaBan = $("#number-search-da-ban");
    dateSearchNgayTao = $("#date-search-ngay-tao");
    selectSearchConHang = $("#select-search-con-hang");
    btnTimKiem = $("#btn-tim-kiem");
    tableDuLieu = $("tbody");
    textTen = $("#text-ten");
    selectDanhMuc = $("#select-danh-muc");
    numberGia = $("#number-gia");
    numberDaBan = $("#number-da-ban");
    numberBaoHanh = $("#number-bao-hanh");
    numberKhuyenMai = $("#number-khuyen-mai");
    fileAnh = $("#file-anh");
    dateNgayTao = $("#date-ngay-tao");
    textareaGioiThieu = $("#textarea-gioi-thieu");
    textareaThongSo = $("#textarea-thong-so");
    checkboxHetHang = $("#checkbox-het-hang");
    btnLuuLai = $("#btn-luu-lai");
    btnXacNhanXoa = $("#btn-xac-nhan-xoa");

    viewDanhSachSanPham();
    searchSanPham();
    xacNhanXoaSanPham();
    luuSanPham();
})

//B2: tạo ra các hàm thao tác
//cần phải thao tác với một list sản phẩm được trả về từ api
function viewDanhSachSanPham() {
    let view = "<tr><td colspan='8'><strong>Không có dữ liệu!</strong></td></tr>";
    //hàm này có chức năng là xóa hết các html cũ và in vào html mới truyền vào
    if(listProduct && listProduct.length > 0) {
        // map thực hiện duyệt lần lượt các phân tử trong mảng và nếu
        //return sẽ trả về 1 mảng mới là kết quả vừa thao tác được.
        view = listProduct.map((data, index) => {
            //teamplate string
            // nó sẽ là một chuỗi cho phép thực hiện các phép toán trong cú pháp ${}
            return `<tr data-index="${index}">
                                <th scope="row">${index + 1}</th>
                                <td><img src="${data.image}"
                                         alt="" width="80px"></td>
                                <td>${viewField(data.name)}</td>
                                <td>${viewField(data.price)}</td>
                                <td>${viewField(data.bouth)}</td>
                                <td>${viewField(data.createDate)}</td>
                                <td class="text-center">${data.soldOut ? `<span class="badge badge-danger">Hết hàng</span>` : `<span class="badge badge-success">Còn hàng</span>`}</td>
                                <td>
                                    <button type="button" class="btn btn-warning sua-san-pham"><i class="fas fa-pen"></i>
                                        Sửa</button>
                                    <button type="button" class="btn btn-danger xoa-san-pham"><i class="fas fa-trash-alt"></i>
                                        Xóa</button>
                                </td>
                            </tr>`
        }).join("");
    }
    tableDuLieu.html(view);
    xoaSanPham();
    suaSanPham();
}

function searchSanPham() {
    //gán sự kiến click cho nút và khi sự kiện xảy ra sẽ thực hiện các lệnh trong function
    btnTimKiem.click(function () {
        //B1: lấy ra các giá trị là các thông tin cần tìm kiếm và xử lý đầu vào các thông tin đấy;
        let valSearchTen = textSearchTen.val();
        let valSearchGia = numberSearchGia.val();
        valSearchGia =  valSearchGia.length > 0 ? valSearchGia : -1;
        let valSearchDaBan = numberSearchDaBan.val();
        valSearchDaBan =  valSearchDaBan.length > 0 ? valSearchDaBan : -1;
        let valSearchNgayTao = dateSearchNgayTao.val();
        valSearchNgayTao =  valSearchNgayTao.length > 0 ? valSearchNgayTao : null;
        //B2: sau khi lấy ra được các giá trị thì goi api search và truyền
        //vào các giá trị cần tìm kiếm
        //Api search sẽ trả về một list sản phẩm tìm kiếm tương ứng
        //Sua đó lấy list trả về từ api gán vào listProduct và view lại list sản phầm
        listProduct = [];
        viewDanhSachSanPham();
    })
}

function xoaSanPham() {
    $(".xoa-san-pham").click(function () {
        //B1: lấy ra index của phần tử trong mảng thông qua thuộc tính data-index trong tr
        //B2: lấy ra id của phần tử tương tứng trong mảng
        //b3: Gọi đến api xóa sản phẩm truyền vào id vừa tìm kiếm được
        //B4: nếu api trả về true thì thực hiện xóa sản phẩm trong list hiện tại và view lại sản phẩm

        //từ khóa this thể hiện là nút khi mình click
        //.parents để lấy ra tr đang chưa nút vừa click
        //.attr("name") trong đấy name  là key mà muốn lấy giá trị.
        indexProduct = $(this).parents("tr").attr("data-index");
        //phải đảm bảo được indexProduct tương ứng với nút xóa mình vừa click;
        $("#exampleModal1").modal("show");
    })
}

function xacNhanXoaSanPham() {
    btnXacNhanXoa.click(function () {
        let idProduct = listProduct[indexProduct - 0].id;
        //call api và truyền vào idProduct và nếu trả về true
        //thực hiện xóa sản phẩm ở trong list;
        listProduct = listProduct.filter((data, index) => {
            return index != indexProduct;
        });
        viewDanhSachSanPham();
        $("#exampleModal1").modal("hide");
    })
}

function suaSanPham() {
    $(".sua-san-pham").click(function () {
        indexProduct = $(this).parents("tr").attr("data-index") - 0;
        elementProduct = listProduct[indexProduct];
        textTen.val(elementProduct.name);
        selectDanhMuc.val(elementProduct.categoryId);
        numberGia.val(elementProduct.price);
        numberDaBan.val(elementProduct.bouth);
        numberBaoHanh.val(elementProduct.guarantee);
        numberKhuyenMai.val(elementProduct.promotion);
        dateNgayTao.val(elementProduct.createDate);
        textareaGioiThieu.val(elementProduct.introduction);
        textareaThongSo.val(elementProduct.specification);
        if(elementProduct.soldOut) {
            checkboxHetHang.prop("checked", true);
        } else {
            checkboxHetHang.prop("checked", false);
        }
        $("#exampleModal").modal("show");
    })
}

function checkData(selector, textError) {
    let val = $(selector).val();
    let check = false;
    if(val.length > 0) {
        check = true;
        hiddenError(selector);
    } else {
        viewError(selector, textError);
    }
    //trả về một đói tượng có 2 thuộc tính là val và check
    //thuộc tính val sẽ mang giá trị của biến val
    // thuộc tính check sẽ mang giá trị của biến check;
    return {val, check};
}

function luuSanPham() {
    btnLuuLai.click(function () {
        //kiểm tra các dữ liệu người dùng nhập vào có đúng định dnajg hay không
        let {val:valTen, check:checkTen} = checkData(textTen, "Định dạng tên chưa đúng");
        let valDanhMuc = selectDanhMuc.val();
        let {val:valGia, check:checkGia}= checkData(numberGia, "Giá bán phải là số");
        let {val:valDaBan, check:checkDaBan}= checkData(numberDaBan, "Nhập số lượng đã bán");
        let {val:valBaoHanh, check:checkBaoHanh}= checkData(numberBaoHanh, "Nhập thời gian bảo hành");
        let {val:valKhuyenMai, check:checkKhuyenMai}= checkData(numberKhuyenMai, "Nhập phần trăm khuyến mãi");
        let valGioiThieu = textareaGioiThieu.val();
        let valThongSo = textareaThongSo.val();
        let valHetHang = checkboxHetHang.is(":checked");
        if(checkTen && checkGia && checkDaBan && checkBaoHanh && checkKhuyenMai) {
            elementProduct.name = valTen;
            elementProduct.categoryId = valDanhMuc;
            elementProduct.price = valGia;
            elementProduct.soldOut = valDaBan;
            elementProduct.guarantee = valBaoHanh;
            elementProduct.promotion = valKhuyenMai;
            elementProduct.introduction = valGioiThieu;
            elementProduct.specification = valThongSo;
            elementProduct.soldOut = valHetHang;
            //call api sửa sản phẩm và truyền vào elementProduct hiện tại
            //Khi api trả về kết quả update thành công thì gán đối tượng
            //vứa trả về vào list với index tương ứng của nó
            listProduct[indexProduct] = elementProduct;
            viewDanhSachSanPham();
            $("#exampleModal").modal("hide");
        }
    })
}
