package erland.webapp.diary.inventory;

public interface ViewInventoryEntriesInterface {
    InventoryEntry[] getEntries();
    InventoryEntryEvent[] getEvents(InventoryEntry entry);
}
