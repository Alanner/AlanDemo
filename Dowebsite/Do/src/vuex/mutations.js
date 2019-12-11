export const userStatus = (state, user) => {
  if (user) {
    state.currentUser = user
    state.isLogin = true
  } else if (user === null) {
    sessionStorage.setItem('user', null)
    state.currentUser = null
    state.isLogin = false
  }
}
