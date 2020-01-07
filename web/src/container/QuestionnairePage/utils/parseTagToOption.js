export function parseTagToOption(tags, type, defaultTags) {
  if (!tags || !type || !defaultTags) {
    return [];
  }

  const filteredDefaultTags = defaultTags.filter(t => t.jhi_type === type);
  const filteredTags = tags.filter(t => t.type === type);

  return filteredDefaultTags.filter(d => filteredTags.find(f => f.id === d.id)).map(d => d.key);
}
