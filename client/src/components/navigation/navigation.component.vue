<template src="./navigation.component.html"></template>
<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';

@Component({
  name: 'Navigation',
})

export default class Navigation extends Vue {

    onCollapse(collapsed: boolean, type: string ) {
      console.log(collapsed, type);
    }

    onBreakpoint(broken: boolean) {
      console.log(broken);
    }
}
</script>

<style lang="scss" src="./navigation.component.scss"></style>
