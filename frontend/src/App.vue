<template>
  <div id="app">
		<loader :loading="isLoading"></loader>
		<div class="container">
			<ul class="nav nav-tabs">
				<li role="presentation" data-toggle="collapse" id="user-li" class="active" @click="open('user')"><a href="#user">單一使用者</a></li>
				<li role="presentation" data-toggle="collapse" id="user-all-li" @click="open('user-all')"><a href="#">所有使用者</a></li>
				<li role="presentation" data-toggle="collapse" id="download-li" @click="open('download')"><a href="#">下載檔案</a></li>
			</ul>
			<div class="container nav-content">
				<div id="user" class="">
					<form class="navbar-form navbar-left" role="search" action="javascript:void(0)">
						<div class="form-group">
							<input type="text" class="form-control" id="inputUserId" placeholder="Search">
						</div>
						<button type="button" @click="getUser()" class="btn btn-default">Submit</button>
					</form>
					<p v-html="user"></p>
				</div>
				<div id="user-all" class="collapse">
					<form class="navbar-form navbar-left" role="search" action="javascript:void(0)">
						<button type="button" @click="getAllUser()" class="btn btn-default">Submit</button>
					</form>
					<p v-for="user in users" :key="user.id" v-html="user"></p>
				</div>
				<div id="download" class="collapse">
					<form class="navbar-form navbar-left" role="search" action="javascript:void(0)">
						<button type="button" @click="download()" class="btn btn-default">Submit</button>
					</form>
				</div>
			</div>
		</div>
		<!-- <img src="./assets/img/AAE93Um.jpg" width="600px;"> -->
  </div>
</template>

<script>
import loader from './components/loader'
import { mapGetters } from 'vuex'
import * as API from './api'

export default {
  name: "app",

  components: { 
		loader
	},

  data: function() {
    return {
			user: null,
			users: []
    }
  },

  computed: {
		...mapGetters('loader', {
			isLoading: 'isLoading'
		})
  },

	methods: {
		open(templateId) {
			document.querySelectorAll(`ul li`).forEach(ele => {
				ele.classList.remove('active');
			})
			document.querySelectorAll(`.nav-content div`).forEach(ele => {
				ele.classList.add('collapse');
			})
			document.querySelector(`#${templateId}-li`).classList.add('active');
			document.querySelector(`#${templateId}`).classList.remove('collapse');
		},
		getUser() {
			let id = window.inputUserId.value;
			const params = {};
			if (id) {
				params.id = id;
			}
			API.getUserInfo(params, (res) => {
				this.user = res
			});
		},
		getAllUser() {
			API.getAllUser({}, (res => this.users = res));
		},
		download() {
			API.download();
		}
	},

  created: function() {
		
	},
	
	mounted() {
		
	}
};
</script>

<style>
#app {
	width: 100%;
}
</style>