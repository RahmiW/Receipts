package org.example.megahottakes.controller;

import org.example.megahottakes.dto.HotTakeDTO;
import org.example.megahottakes.services.ReceiptService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PatchMapping("/{hotTakeId}/pull")
    public HotTakeDTO pullReceipt(@PathVariable Long hotTakeId, @RequestAttribute Long authUserId) {
        return receiptService.pullReceipt(authUserId, hotTakeId);
    }
}
