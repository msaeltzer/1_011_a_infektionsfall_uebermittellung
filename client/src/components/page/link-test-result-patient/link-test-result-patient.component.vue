
<template src="./link-test-result-patient.component.html"></template>
<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';
import Login from "../../laboratory/login/login.component.vue";
import LinkTestResultAndPatient from "../../laboratory/link-test-result-patient-lb/link-test-result-patient-lb.component.vue";

@Component({
  name: 'LinkTestResultPatientComponent',
  components: {
      Login,
      LinkTestResultAndPatient
  }
})

export default class LinkTestPatientComponent extends Vue {

    // sollte hier nicht watch verwendet werden ? 
    activeKey: string;
    isLoggedIn: boolean;
    laboratoryId: string;

    constructor(){
        super();
        this.activeKey = "1";
        this.isLoggedIn = false;
        this.laboratoryId = "";
    }

    onLoginSuccess(laboratoryId: string) {
      this.laboratoryId = laboratoryId;
      this.isLoggedIn = true;
      this.activeKey = "2";
    }
}
</script>

<style lang="scss" src="./link-test-result-patient.component.scss"></style>