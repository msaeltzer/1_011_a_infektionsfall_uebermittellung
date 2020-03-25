<template src="./patient-overview.component.html"></template>
<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';

@Component({
  name: 'TestingComponent',
})

export default class Navigation extends Vue {

    public entities: Array<any> = [];
    public content: any;
    public firstName: string;
    public lastName: string;
    public dateOfBirth: any;
    public gender: any;
    public email: string;
    public phoneNumber: string;
    public street: string;
    public zip: string;
    public houseNumber: string;
    public city: string;
    public uuid: string;
    public insuranceCompany: string;
    public insuranceMembershipNumber: string;

    @Prop()
    public msg: string = '';

    constructor(){
        super();
        this.uuid = "#2685896023";
        this.firstName = "Max";
        this.lastName = "Mustermann";
        this.dateOfBirth = "21.3.1995";
        this.gender = "männlich";
        this.email = "max@mustermann.de";
        this.phoneNumber = "+49 12354566";
        this.street = "Musterstraße";
        this.zip = "12345";
        this.houseNumber = "1";
        this.city = "Musterstadt";
        this.insuranceCompany = "Musterversicherung";
        this.insuranceMembershipNumber = "45687912";     
    }

    requestTestAgain(type: 'success' | 'warn' | 'info' | 'error') {
        // const patID = "12389384"

        // Check notification type (success, info, warning, error)
        if (type === "success") {
            this.$notification.success({
                message:  'Der Test wurde erneut angefordert.', 
                description: ''
            });
        }
    }

}
</script>

<style lang="scss" src="./patient-overview.component.scss"></style>
