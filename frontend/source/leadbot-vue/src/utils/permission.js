export const ROLE = {
  ADMIN: 'ADMIN',
  MANAGER: 'MANAGER',
  USER: 'USER'
}

export function hasRole(user, roles = []) {
  if (!user || !user.role) return false
  return roles.includes(user.role)
}

export function canManageMembers(user) {
  return hasRole(user, [ROLE.ADMIN])
}

export function canManageRules(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER])
}

export function canManageChannels(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER])
}

export function canDeleteLead(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER])
}

export function canViewReports(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER])
}

export function canViewTeam(user) {
  return hasRole(user, [ROLE.ADMIN])
}

export function canEditLead(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER, ROLE.USER])
}

export function canUseChat(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER, ROLE.USER])
}

export function canViewTasks(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER, ROLE.USER])
}

export function canViewLeads(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER, ROLE.USER])
}

export function canChangePassword(user) {
  return hasRole(user, [ROLE.ADMIN, ROLE.MANAGER, ROLE.USER])
}
