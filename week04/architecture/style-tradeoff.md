# Layered vs Microkernel for Plugin-based CMS

Reference framing from architecture literature:
- *Software Architecture: The Hard Parts* (Ford, Richards, et al.): trade-off analysis and architecture characteristics.
- *Fundamentals of Software Architecture* (Richards, Ford): modularity, maintainability, and deployability concerns.
- *Patterns of Enterprise Application Architecture* (Fowler): separation of responsibilities and layering.

## 1. Layered Architecture

Advantages:
- Clear separation of concerns (UI, API, application, domain, infrastructure).
- Easier for teams to maintain and test each layer.
- Strong boundary for business logic and data access.

Disadvantages:
- New features touching many layers can increase development overhead.
- Harder to add optional runtime features without extension point design.

## 2. Microkernel Architecture

Advantages:
- Extensibility is a first-class concern: add new plugins with minimal core change.
- Supports variable product capabilities per deployment.
- Core remains stable while plugins evolve independently.

Disadvantages:
- Plugin lifecycle and compatibility management become complex.
- Runtime behavior is less predictable if many plugins interact.
- Debugging cross-plugin side effects can be harder.

## 3. Recommended Style for this CMS

Best fit: Hybrid with Microkernel as primary style and Layered as internal structure.

Reasoning:
- This CMS explicitly targets plugin-based evolution, so microkernel solves the main architectural driver.
- Layered architecture inside the kernel keeps code maintainable and testable.
- Hybrid approach balances extensibility (microkernel) and long-term maintainability (layered).

When to keep this choice:
- Product roadmap includes frequent capability extension via plugins.
- Teams want stable core while allowing feature experimentation.

When to reconsider:
- If plugin isolation, independent release cycles, or untrusted third-party plugins become critical, move from in-process plugins to out-of-process services.
