import defaultSettings from '@/settings'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${defaultSettings.title}`
  }
  return `${defaultSettings.title}`
}
