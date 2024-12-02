package edu.northeastern.numad24fa_group15project.controllers;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.*;

public class ChipGroupManager {
    private final Context context;
    private final Map<ChipGroupType, List<String>> selections;

    public enum ChipGroupType {
        INTERESTS,
        DIETARY_RESTRICTIONS,
        FILTERS
    }

    public ChipGroupManager(Context context) {
        this.context = context;
        this.selections = new EnumMap<>(ChipGroupType.class);
    }

    public void createChips(ChipGroup chipGroup, ChipGroupType type, String[] options) {
        chipGroup.removeAllViews();
        for (String option : options) {
            Chip chip = new Chip(context);
            chip.setText(option);
            chip.setCheckable(true);
            chip.setCheckedIconVisible(true);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateSelection(type, option, isChecked);
            });
            chipGroup.addView(chip);
        }
    }

    private void updateSelection(ChipGroupType type, String option, boolean isSelected) {
        selections.computeIfAbsent(type, k -> new ArrayList<>());
        List<String> typeSelections = selections.get(type);
        if (isSelected) {
            if (!typeSelections.contains(option)) {
                typeSelections.add(option);
            }
        } else {
            typeSelections.remove(option);
        }
    }

    public Map<String, List<String>> getAllSelections() {
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<ChipGroupType, List<String>> entry : selections.entrySet()) {
            result.put(entry.getKey().name().toLowerCase(), entry.getValue());
        }
        return result;
    }
}