
// Gives user prompt to confirm page deletion
$(function (){

    $("a.confirmDeletion").click(function (){
        if(!confirm("Confirm deletion")) return false;
    });
});

