
<template src="./link-test-patient.component.html"></template>
<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';
import Login from "../../laboratory/login/login.component.vue";
import LinkTestAndPatient from "../../laboratory/link-test-patient-lb/link-test-patient-lb.component.vue";

@Component({
  name: 'LinkTestPatientComponent',
  components: {
      Login,
      LinkTestAndPatient
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

<style lang="scss" src="./link-test-patient.component.scss"></style>