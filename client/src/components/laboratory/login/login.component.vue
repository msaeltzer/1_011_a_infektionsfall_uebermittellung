<template src="./login.component.html"></template>
<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';

@Component({
  name: 'LoginComponent',
})

export default class LoginComponent extends Vue {


    @Prop()
    title: string; 

    public form: any; 

    constructor(){
        super();
        this.form = this.$form.createForm(this)
        this.title = ''
    }

    handleLogin(e: any) {
      e.preventDefault();

      this.form.validateFields((err: any, values: any) => {
        if (err) {
          return;
        }

        if (values.id && values.password === "asdf") {
          this.$emit("on-login-success", values.id);
        } else {
          this.$notification["error"]({
            message: "Login Fehler",
            description: "Kennung und / oder Password nicht korrekt."
          });
        }
      });
    }
}
</script>

<style lang="scss" src="./login.component.scss"></style>