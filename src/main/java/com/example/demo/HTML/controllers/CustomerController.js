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
                // clear();
                $("#customer-btns>button[type='reset']").click();
                loadCustomers();
                console.log(JSON.stringify(res))

                Swal.fire({
                    icon: 'success',
                    title: 'Customer has been saved successfully!',
                    showConfirmButton: false,
                    timer: 2000
                })
            },
            error: (err)=>{
                console.error(err)
                Swal.fire({
                    icon: 'error',
                    title: 'Invalid Input',
                    text: 'Something went wrong!'
                })
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

// Customer Delete
$("#customer-btns>button[type='button']").eq(2).on("click", () => {
    let id = $("#id").val();
    console.log(id);
    console.log("Delete BTN");

    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire(
                'Deleted!',
                'Your file has been deleted.',
                'success'
            )
            $.ajax({
                url: "http://localhost:8080/Demo_war_exploded/customer?id="+id,
                type: "DELETE",
                success: function () {
                    // clear();
                    $("#customer-btns>button[type='reset']").click();
                    loadCustomers();
                }
            });
        }
    })
})

// Customer Update
$("#customer-btns > button[type='button']").eq(1).on("click", () => {
    event.preventDefault();

    let idF = $("#id").val();
    let cnameF = $("#customer_name").val();
    let cMobileF = $("#customer_mobile").val();
    let cAddressF = $("#customer_address").val();

    console.log('Customer Id :', idF);
    console.log('Customer Name :', cnameF);
    console.log('Customer Mobile :', cMobileF);
    console.log('Customer Address :', cAddressF);

    const customerData = {
        id: idF,
        name: cnameF,
        mobile: cMobileF,
        address: cAddressF
    };
    console.log(customerData);

    // create JSON
    const customerJSON = JSON.stringify(customerData);
    console.log(customerJSON);

    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/customer",
        type: "PUT",
        data: customerJSON,
        headers: { "Content-Type": "application/json" },
        success: (res) => {
            // clear();
            $("#customer-btns>button[type='reset']").click();
            loadCustomers();
            console.log(JSON.stringify(res));

            Swal.fire({
                icon: 'success',
                title: 'Customer has been updated successfully!',
                showConfirmButton: false,
                timer: 2000
            })
        },
        error: (err) => {
            console.error(err);
            Swal.fire({
                icon: 'error',
                title: 'Invalid Input',
                text: 'Something went wrong!'
            })
        }
    });
});

