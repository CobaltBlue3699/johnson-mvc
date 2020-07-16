<template>
	<div v-show="loading" class="loading">
    <div class="tile tile1"></div>
    <div class="tile tile2"></div>
    <div class="tile tile3"></div>
    <div class="tile tile4"></div>
    <div class="tile tile5"></div>
  </div>
</template>
<script>
export default {
	name: 'loader',
	props: ['loading'],
}
</script>
<style lang="css">
	/***** loading <div> css *****/
.loading {
  height: 100%;
  width: 100%;
  left: 0%;
  top: 0%;
  position: fixed;
	justify-content: center;
  align-items: center;
  /* background: rgba(0,0,0,0.6); */
  z-index: 9999;
  display: flex;
}

.tile {
	/* swap width, height values and uncomment width in keyframes at 50% for other way round animation */
	width: 10px; 
	height: 1px;
	background: grey;
	border-radius: 50px;
	box-shadow: 0px 0px 10px grey;
	margin: 2px;
	animation: load 1s infinite;
}

.tile1 {
	animation-delay: 0.1s;
}
.tile2 {
	animation-delay: 0.2s;
}
.tile3 {
	animation-delay: 0.3s;
}
.tile4 {
	animation-delay: 0.4s;
}
.tile5 {
	animation-delay: 0.5s;
}

@keyframes load {
	0% {
		background: white;
	}
	50% {
		height: 60px;
/* 		width:10px; */
	}
	100% {
		background: #2250ac;
	}
}
</style>