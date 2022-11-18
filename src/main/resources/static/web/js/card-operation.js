var app = new Vue({
    el: "#app",
    data: {
        // loanTypes: [],
        // loanTypeId: 0,
        // payments: 0,
        // paymentsList: [],
        // clientAccounts: [],
        cardNumber: "",
        cvv: "",
        description: "",
        errorToats: null,
        errorMsg: null,
        amount: 0,
        fees: []
    },
    methods: {
        getData: function(){
            Promise.all([axios.get("/api/cardOperation"),axios.get("/api/clients/current/accounts")])
            .then((response) => {
                //get loan types ifo
                this.loanTypes = response[0].data;
                this.clientAccounts = response[1].data;
            })
            .catch((error) => {
                this.errorMsg = "Error getting data";
                this.errorToats.show();
            })
        }
        ,
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        checkApplication: function () {
            if (this.cardNumber == "") {
                this.errorMsg = "Complete the Card Number field";
                this.errorToats.show();
            } else if (this.cvv == 0) {
                this.errorMsg = "Complete the CVV field";
                this.errorToats.show();
            } else if (this.amount == 0) {
                this.errorMsg = "You must indicate an amount";
                this.errorToats.show();
            } else if (this.description == "") {
                this.errorMsg = "Complete the description field";
                this.errorToats.show();
            } else {
                this.modal.show();
            }
        },
        apply: function () {
            axios.post("/api/cardOperation", {
                cardNumber: this.cardNumber,
                cvv: this.cvv,
                amount: this.amount,
                description: this.description
            })
                .then(response => {
                    this.modal.hide();
                    this.okmodal.show();
                })
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        },
        finish: function () {
            window.location.reload();
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.okmodal = new bootstrap.Modal(document.getElementById('okModal'));
        this.feesmodal = new bootstrap.Modal(document.getElementById('feesModal'));
        this.getData();
    }
})