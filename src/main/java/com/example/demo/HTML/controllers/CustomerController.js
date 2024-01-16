loadCustomers();
var row_index = null;


// Customer Save
$(document).ready(function() {
    $("#customer-btns > button[type='button']").eq(0).on("click", () => {
        event.preventDefault();

        let idF = $("#id").val();
        let cnameF = $("#customer_name").val();
        let cMobileF = $("#customer_mobile").val();
        let cAddressF = $("#customer_address").val();

        console.log('Customer Id :',idF);
        console.log('Customer Name :',cnameF);
        console.log('Customer Mobile :',cMobileF);
        console.log('Customer Address :',cAddressF);

        const customerData = {
            id:idF,
            name:cnameF,
            mobile:cMobileF,
            address:cAddressF
        };
        console.log(customerData);

        // create JSON
        const customerJSON = JSON.stringify(customerData)
        console.log(customerJSON)

        $.ajax({
            url:"http://localhost:8080/Demo_war_exploded/customer",
            type:"POST",
            data:customerJSON,
            headers:{"Content-Type":"application/json"},
            success: (res) =>{
                loadCustomers();
                console.log(JSON.stringify(res))
            },
            error: (err)=>{
                console.error(err)
            }
        });


    });
});

// get all customers data
function loadCustomers() {
    $("#customer-tbl-body").empty();

    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/customer",
        type: "GET",
        success: function(data) {
            data.forEach(function(customer) {
                let record = `<tr><td class="id">${customer.id}</td><td class="name">${customer.name}</td><td class="mobile">${customer.mobile}</td><td class="address">${customer.address}</td></tr>`;
                $("#customer-tbl-body").append(record);
            });
        }
    });
}

// when click a row
$("#customer-tbl-body").on("click", "tr", function() {
    row_index = $(this).index();

    let id = $(this).find(".id").text();
    let name = $(this).find(".name").text();
    let mobile = $(this).find(".mobile").text();
    let address = $(this).find(".address").text();

    $("#id").val(id);
    $("#customer_name").val(name);
    $("#customer_mobile").val(mobile);
    $("#customer_address").val(address);
})

