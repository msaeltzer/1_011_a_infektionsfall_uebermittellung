<template src="./App.component.html"></template>
<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import Navigation from './components/navigation/navigation.component.vue';

@Component({
  name: 'App',
  components: {
    Navigation,
  }
})
export default class App extends Vue {

}
</script>

<style lang="scss" src="./App.component.scss"></style>
