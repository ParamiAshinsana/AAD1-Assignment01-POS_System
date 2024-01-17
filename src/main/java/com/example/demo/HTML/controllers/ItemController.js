loadItems();
var row_index = null;

// Item Save
$(document).ready(function() {
    $("#item-btns> button[type='button']").eq(0).on("click", () => {
        event.preventDefault();

        let icodeF = $("#item_code").val();
        let inameF = $("#item_name").val();
        let ipriceF = $("#item_price").val();
        let iqtyF = $("#item_qty").val();

        console.log('Item Code :',icodeF);
        console.log('Item Name :',inameF);
        console.log('Item Price :',ipriceF);
        console.log('Item Quantity :',iqtyF);

        const itemData = {
            icode:icodeF,
            iname:inameF,
            iprice:ipriceF,
            iqty:iqtyF
        };
        console.log(itemData);

        // create JSON
        const itemJSON = JSON.stringify(itemData)
        console.log(itemJSON)

        $.ajax({
            url:"http://localhost:8080/Demo_war_exploded/item",
            type:"POST",
            data:itemJSON,
            headers:{"Content-Type":"application/json"},
            success: (res) =>{
                // clear();
                $("#item-btns>button[type='reset']").click();
                loadItems();
                console.log(JSON.stringify(res))

                Swal.fire({
                    icon: 'success',
                    title: 'Item has been saved successfully!',
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
function loadItems() {
    $("#item-tbl-body").empty();

    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/item",
        type: "GET",
        success: function(data) {
            data.forEach(function(item) {
                let record = `<tr><td class="icode">${item.icode}</td><td class="iname">${item.iname}</td><td class="iprice">${item.iprice}</td><td class="iqty">${item.iqty}</td></tr>`;
                $("#item-tbl-body").append(record);
            });
        }
    });
}

// when click a row
$("#item-tbl-body").on("click", "tr", function() {
    row_index = $(this).index();

    let icode = $(this).find(".icode").text();
    let iname = $(this).find(".iname").text();
    let iprice = $(this).find(".iprice").text();
    let iqty = $(this).find(".iqty").text();

    $("#item_code").val(icode);
    $("#item_name").val(iname);
    $("#item_price").val(iprice);
    $("#item_qty").val(iqty);
})

// Item Delete
$("#item-btns>button[type='button']").eq(2).on("click", () => {
    let code = $("#item_code").val();
    console.log(code);
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
                url: "http://localhost:8080/Demo_war_exploded/item?icode="+code,
                type: "DELETE",
                success: function () {
                    // clear();
                    $("#item-btns>button[type='reset']").click();
                    loadItems();
                }
            });
        }
    })
})

// Item Update
$("#item-btns> button[type='button']").eq(1).on("click", () => {
    event.preventDefault();

    let icodeF = $("#item_code").val();
    let inameF = $("#item_name").val();
    let ipriceF = $("#item_price").val();
    let iqtyF = $("#item_qty").val();

    console.log('Item Code :',icodeF);
    console.log('Item Name :',inameF);
    console.log('Item Price :',ipriceF);
    console.log('Item Quantity :',iqtyF);

    const itemData = {
        icode:icodeF,
        iname:inameF,
        iprice:ipriceF,
        iqty:iqtyF
    };
    console.log(itemData);

    // create JSON
    const itemJSON = JSON.stringify(itemData);
    console.log(itemJSON);

    $.ajax({
        url: "http://localhost:8080/Demo_war_exploded/item",
        type: "PUT",
        data: itemJSON,
        headers: { "Content-Type": "application/json" },
        success: (res) => {
            // clear();
            $("#item-btns>button[type='reset']").click();
            loadItems();
            console.log(JSON.stringify(res));

            Swal.fire({
                icon: 'success',
                title: 'Item has been updated successfully!',
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
